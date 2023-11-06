import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('PreferredPurchaseChannel e2e test', () => {
  const preferredPurchaseChannelPageUrl = '/preferred-purchase-channel';
  const preferredPurchaseChannelPageUrlPattern = new RegExp('/preferred-purchase-channel(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const preferredPurchaseChannelSample = { value: 'Metal salmon', language: 'ENGLISH' };

  let preferredPurchaseChannel;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/preferred-purchase-channels+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/preferred-purchase-channels').as('postEntityRequest');
    cy.intercept('DELETE', '/api/preferred-purchase-channels/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (preferredPurchaseChannel) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/preferred-purchase-channels/${preferredPurchaseChannel.id}`,
      }).then(() => {
        preferredPurchaseChannel = undefined;
      });
    }
  });

  it('PreferredPurchaseChannels menu should load PreferredPurchaseChannels page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('preferred-purchase-channel');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PreferredPurchaseChannel').should('exist');
    cy.url().should('match', preferredPurchaseChannelPageUrlPattern);
  });

  describe('PreferredPurchaseChannel page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(preferredPurchaseChannelPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PreferredPurchaseChannel page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/preferred-purchase-channel/new$'));
        cy.getEntityCreateUpdateHeading('PreferredPurchaseChannel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', preferredPurchaseChannelPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/preferred-purchase-channels',
          body: preferredPurchaseChannelSample,
        }).then(({ body }) => {
          preferredPurchaseChannel = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/preferred-purchase-channels+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/preferred-purchase-channels?page=0&size=20>; rel="last",<http://localhost/api/preferred-purchase-channels?page=0&size=20>; rel="first"',
              },
              body: [preferredPurchaseChannel],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(preferredPurchaseChannelPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PreferredPurchaseChannel page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('preferredPurchaseChannel');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', preferredPurchaseChannelPageUrlPattern);
      });

      it('edit button click should load edit PreferredPurchaseChannel page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PreferredPurchaseChannel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', preferredPurchaseChannelPageUrlPattern);
      });

      it('edit button click should load edit PreferredPurchaseChannel page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PreferredPurchaseChannel');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', preferredPurchaseChannelPageUrlPattern);
      });

      it('last delete button click should delete instance of PreferredPurchaseChannel', () => {
        cy.intercept('GET', '/api/preferred-purchase-channels/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('preferredPurchaseChannel').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', preferredPurchaseChannelPageUrlPattern);

        preferredPurchaseChannel = undefined;
      });
    });
  });

  describe('new PreferredPurchaseChannel page', () => {
    beforeEach(() => {
      cy.visit(`${preferredPurchaseChannelPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PreferredPurchaseChannel');
    });

    it('should create an instance of PreferredPurchaseChannel', () => {
      cy.get(`[data-cy="value"]`).type('Security').should('have.value', 'Security');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        preferredPurchaseChannel = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', preferredPurchaseChannelPageUrlPattern);
    });
  });
});

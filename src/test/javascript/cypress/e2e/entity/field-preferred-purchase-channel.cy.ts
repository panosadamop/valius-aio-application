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

describe('FieldPreferredPurchaseChannel e2e test', () => {
  const fieldPreferredPurchaseChannelPageUrl = '/field-preferred-purchase-channel';
  const fieldPreferredPurchaseChannelPageUrlPattern = new RegExp('/field-preferred-purchase-channel(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const fieldPreferredPurchaseChannelSample = {};

  let fieldPreferredPurchaseChannel;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/field-preferred-purchase-channels+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/field-preferred-purchase-channels').as('postEntityRequest');
    cy.intercept('DELETE', '/api/field-preferred-purchase-channels/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (fieldPreferredPurchaseChannel) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/field-preferred-purchase-channels/${fieldPreferredPurchaseChannel.id}`,
      }).then(() => {
        fieldPreferredPurchaseChannel = undefined;
      });
    }
  });

  it('FieldPreferredPurchaseChannels menu should load FieldPreferredPurchaseChannels page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('field-preferred-purchase-channel');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FieldPreferredPurchaseChannel').should('exist');
    cy.url().should('match', fieldPreferredPurchaseChannelPageUrlPattern);
  });

  describe('FieldPreferredPurchaseChannel page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(fieldPreferredPurchaseChannelPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FieldPreferredPurchaseChannel page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/field-preferred-purchase-channel/new$'));
        cy.getEntityCreateUpdateHeading('FieldPreferredPurchaseChannel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPreferredPurchaseChannelPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/field-preferred-purchase-channels',
          body: fieldPreferredPurchaseChannelSample,
        }).then(({ body }) => {
          fieldPreferredPurchaseChannel = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/field-preferred-purchase-channels+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/field-preferred-purchase-channels?page=0&size=20>; rel="last",<http://localhost/api/field-preferred-purchase-channels?page=0&size=20>; rel="first"',
              },
              body: [fieldPreferredPurchaseChannel],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(fieldPreferredPurchaseChannelPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FieldPreferredPurchaseChannel page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('fieldPreferredPurchaseChannel');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPreferredPurchaseChannelPageUrlPattern);
      });

      it('edit button click should load edit FieldPreferredPurchaseChannel page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldPreferredPurchaseChannel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPreferredPurchaseChannelPageUrlPattern);
      });

      it('edit button click should load edit FieldPreferredPurchaseChannel page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldPreferredPurchaseChannel');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPreferredPurchaseChannelPageUrlPattern);
      });

      it('last delete button click should delete instance of FieldPreferredPurchaseChannel', () => {
        cy.intercept('GET', '/api/field-preferred-purchase-channels/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('fieldPreferredPurchaseChannel').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPreferredPurchaseChannelPageUrlPattern);

        fieldPreferredPurchaseChannel = undefined;
      });
    });
  });

  describe('new FieldPreferredPurchaseChannel page', () => {
    beforeEach(() => {
      cy.visit(`${fieldPreferredPurchaseChannelPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FieldPreferredPurchaseChannel');
    });

    it('should create an instance of FieldPreferredPurchaseChannel', () => {
      cy.get(`[data-cy="preferredPurchaseChannel"]`).type('City').should('have.value', 'City');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        fieldPreferredPurchaseChannel = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', fieldPreferredPurchaseChannelPageUrlPattern);
    });
  });
});

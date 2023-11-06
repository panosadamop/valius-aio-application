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

describe('PreferredCommunicationChannel e2e test', () => {
  const preferredCommunicationChannelPageUrl = '/preferred-communication-channel';
  const preferredCommunicationChannelPageUrlPattern = new RegExp('/preferred-communication-channel(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const preferredCommunicationChannelSample = { value: 'bluetooth Kentucky', language: 'ENGLISH' };

  let preferredCommunicationChannel;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/preferred-communication-channels+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/preferred-communication-channels').as('postEntityRequest');
    cy.intercept('DELETE', '/api/preferred-communication-channels/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (preferredCommunicationChannel) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/preferred-communication-channels/${preferredCommunicationChannel.id}`,
      }).then(() => {
        preferredCommunicationChannel = undefined;
      });
    }
  });

  it('PreferredCommunicationChannels menu should load PreferredCommunicationChannels page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('preferred-communication-channel');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PreferredCommunicationChannel').should('exist');
    cy.url().should('match', preferredCommunicationChannelPageUrlPattern);
  });

  describe('PreferredCommunicationChannel page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(preferredCommunicationChannelPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PreferredCommunicationChannel page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/preferred-communication-channel/new$'));
        cy.getEntityCreateUpdateHeading('PreferredCommunicationChannel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', preferredCommunicationChannelPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/preferred-communication-channels',
          body: preferredCommunicationChannelSample,
        }).then(({ body }) => {
          preferredCommunicationChannel = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/preferred-communication-channels+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/preferred-communication-channels?page=0&size=20>; rel="last",<http://localhost/api/preferred-communication-channels?page=0&size=20>; rel="first"',
              },
              body: [preferredCommunicationChannel],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(preferredCommunicationChannelPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PreferredCommunicationChannel page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('preferredCommunicationChannel');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', preferredCommunicationChannelPageUrlPattern);
      });

      it('edit button click should load edit PreferredCommunicationChannel page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PreferredCommunicationChannel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', preferredCommunicationChannelPageUrlPattern);
      });

      it('edit button click should load edit PreferredCommunicationChannel page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PreferredCommunicationChannel');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', preferredCommunicationChannelPageUrlPattern);
      });

      it('last delete button click should delete instance of PreferredCommunicationChannel', () => {
        cy.intercept('GET', '/api/preferred-communication-channels/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('preferredCommunicationChannel').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', preferredCommunicationChannelPageUrlPattern);

        preferredCommunicationChannel = undefined;
      });
    });
  });

  describe('new PreferredCommunicationChannel page', () => {
    beforeEach(() => {
      cy.visit(`${preferredCommunicationChannelPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PreferredCommunicationChannel');
    });

    it('should create an instance of PreferredCommunicationChannel', () => {
      cy.get(`[data-cy="value"]`).type('copying calculate AGP').should('have.value', 'copying calculate AGP');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        preferredCommunicationChannel = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', preferredCommunicationChannelPageUrlPattern);
    });
  });
});
